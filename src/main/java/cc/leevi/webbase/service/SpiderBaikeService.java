package cc.leevi.webbase.service;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Iterator;

/**
 * 百科爬虫服务
 */
public class SpiderBaikeService implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    @Override
    public Site getSite() {
        return site;
    }

    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(https://baike.baidu\\.com/\\w+/\\w+)").all());
//        page.putField("author", page.getUrl().regex("https://baike.baidu\\.com/(\\w+)/.*").toString());
        page.putField("title", page.getHtml().xpath("//h1/text()").toString());
//        page.putField("name", page.getHtml().xpath("//div[@class='para']/text()").all().iterator());
        Iterator iterator = page.getHtml().xpath("//div[@class='para']/text()").all().iterator();
        String baikedatas = "";
        while (iterator.hasNext()) {
            baikedatas = baikedatas + String.valueOf(iterator.next());
        }
        page.putField("name", baikedatas);
        if (page.getResultItems().get("name") == null || page.getResultItems().get("name") == "") {
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("tidyText()"));
    }
}
