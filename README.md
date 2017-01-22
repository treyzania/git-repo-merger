# Git Repositoriy Merger

## Building

To build it, run `mvn install`, which will put a built jar into the `target` directory.  You'll need Maven 4 but that're pretty easy to get if you don't have it already.

## Running

Now if we assume that you've made the `$GRM` environmental variable as *some command* that'll run GRM, then you're ready to build.

Let's say that you have the following repo in the following relative directories you want to move to repos in the target repo:

* `foo/bar` -> `proj/repo1`
* `baz/repo2` -> `proj/repo2`
* `quux/repo3` -> `proj/repo3`

The syntax for a merger is `<src>:<dest>`, so if we want to make the merged repo in the `destproj` directory, then the syntax is the following:

    you@machine:~$ $GRM destproj foo/bar:proj/repo1 baz/repo2:proj/repo2 quux/repo3:proj/repo3

Now this'll probably take a really long time as it's doing a *ton* of work moving things around because it's a little naive about when to copy files, so if you want to see some cool things going on then run `watch -n 0.1 ps auxf` and look for the `java` process for GRM.  It'll probably have some kind of git command running underneath it a lot.
