#!/bin/bash

PROJPATH=$(dirname $(realpath $0))
pushd $(pwd) > /dev/null
cd $PROJPATH
mvn clean package > /dev/null
popd > /dev/null

echo DANK $@

JARPATH=$(realpath $PROJPATH/target/git-repo-merger-*.jar)
java -cp $JARPATH com.treyzania.grm.GRM $@
