# actors-vs-single-thread-pool
divide work into multiple actors vs whole work in single thread pool


two backdoor to trigger test:

1: `http://localhost:8080/demo/akka?taskcnt=1000`

send 1000 tasks to akka actors

2: `http://localhost:8080/demo/threadpool?taskcnt=1000`

send 1000 tasks to single thread pool


