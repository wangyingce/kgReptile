package cc.leevi.webbase.controller;

import cc.leevi.webbase.pipeline.BaikeSaveNeo4jPipeline;
import cc.leevi.webbase.pipeline.BaikeSpiderPipline;
import cc.leevi.webbase.pipeline.Neo4jSavePipeline;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

@RestController
@RequestMapping
public class SpiderController {
    /***
     * n4j落库测试-1
     * @return
     * @throws Exception
     */
    @GetMapping("sdn4jTst1")
    public Object spiderDataTest() throws Exception {
//        Spider.create(new SpiderBaikeService()).addUrl("https://baike.baidu.com/item/保单").addPipeline(new ConsolePipeline()).addPipeline(new Neo4jSavePipeline()).thread(5).run();
        Spider.create(new BaikeSpiderPipline()).addUrl("https://baike.baidu.com/item/理赔").addPipeline(new Neo4jSavePipeline()).thread(5).run();
        return null;
    }

    /***
     * 单词百科爬虫和图谱组织
     * @param wds
     * @return
     * @throws Exception
     */
    @GetMapping("SpiderBaike")
    public Object spiderDataTest(String wds) throws Exception {
        Spider.create(new BaikeSpiderPipline()).addUrl("https://baike.baidu.com/item/"+wds).addPipeline(new Neo4jSavePipeline()).thread(5).run();
        return null;
    }

}