package com.yifan.benchmark.akka.akkabenchmark;

import com.yifan.benchmark.akka.akkabenchmark.akka.AkkaTopo;
import com.yifan.benchmark.akka.akkabenchmark.threadpool.ThreadPoolTopo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired private ThreadPoolTopo threadPoolTopo;

    @Autowired private AkkaTopo akkaTopo;


    @RequestMapping("/threadpool") @ResponseBody
    public String threadpool(@RequestParam("taskcnt") long taskCnt) {
        threadPoolTopo.submitTask(taskCnt);
        return  "ok";
    }

    @RequestMapping("/akka") @ResponseBody
    public String akka(@RequestParam("taskcnt") long taskCnt) {
        akkaTopo.addTask(taskCnt);
        akkaTopo.launch();

        return  "ok";
    }
}
