//package cc.leevi.webbase.controller;
//import us.codecraft.webmagic.Page;
//import us.codecraft.webmagic.Site;
//import us.codecraft.webmagic.processor.PageProcessor;
//import java.util.Iterator;
//
//public class SpiderController implements PageProcessor {
//
//    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
//
//    public void process(Page page) {
//        page.addTargetRequests(page.getHtml().links().regex("(https://baike.baidu\\.com/\\w+/\\w+)").all());
////        page.putField("author", page.getUrl().regex("https://baike.baidu\\.com/(\\w+)/.*").toString());
//        page.putField("title", page.getHtml().xpath("//h1/text()").toString());
////        page.putField("name", page.getHtml().xpath("//div[@class='para']/text()").all().iterator());
//        Iterator iterator = page.getHtml().xpath("//div[@class='para']/text()").all().iterator();
//        String baikedatas = "";
//        while (iterator.hasNext()) {
//            baikedatas = baikedatas + String.valueOf(iterator.next());
//        }
//        page.putField("name", baikedatas);
//
//        if (page.getResultItems().get("name") == null || page.getResultItems().get("name") == "") {
//            //skip this page
//            page.setSkip(true);
//        }
//        page.putField("readme", page.getHtml().xpath("tidyText()"));
//    }
//
//    @Override
//    public Site getSite() {
//        return site;
//    }
//
////    public static void main(String[] args) {
////        Spider sp = Spider.create(new SpiderController());
////        sp.addUrl("https://baike.baidu.com/item/保单").addPipeline(new ConsolePipeline()).addPipeline(new JsonFilePipeline("F:\\ATE\\KGSP\\webmagic\\")).thread(5).run();
////        sp.addUrl("https://baike.baidu.com/item/保单").addPipeline(new ConsolePipeline()).addPipeline(new Neo4jSavePipeline()).thread(5).run();
////    }
//}