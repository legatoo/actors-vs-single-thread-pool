# actors-vs-single-thread-pool
divide work into multiple actors vs whole work in single thread pool


two backdoor to trigger test:

1: `http://localhost:8080/demo/akka?taskcnt=10000`

send 10000 tasks to akka actors, below is the thread view from jprofile. whole work takes many many mintues(about 10mins)

![akka](https://user-images.githubusercontent.com/1506580/38158714-859504e6-34cc-11e8-9adf-2a11ba924d52.png)

2: `http://localhost:8080/demo/threadpool?taskcnt=10000`

send 10000 tasks to single thread pool, , below is the thread view from jprofile. whole work finished in 50s

![threadpool](https://user-images.githubusercontent.com/1506580/38158662-a02c9ebe-34cb-11e8-9fa5-8cba3ffeb997.png)
