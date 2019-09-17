package cc.leevi.webbase.pipeline;
import cc.leevi.webbase.service.KgFusionService;
import cc.leevi.webbase.support.SpringApplicationContextHolder;
import cc.leevi.webbase.vo.BaikeDataVo;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaikeSaveNeo4jPipeline implements Pipeline {
    private KgFusionService kgFusionService;

    /**
     * neo4j落库流水线
     */
    private void ensureService(){
        if(kgFusionService == null){
            kgFusionService = SpringApplicationContextHolder.getBean(KgFusionService.class);
        }
    }

    public void process(ResultItems resultItems, Task task) {
        ensureService();
        /**处理resultItems*/
        kgFusionService.createBaikeTitle(resultItems.getAll().get("title").toString(),resultItems.getAll().get("titleValue").toString());
        List<BaikeDataVo> baikeVos = (List <BaikeDataVo>) resultItems.getAll().get("baikeNodes");
        for(BaikeDataVo baikeVo:baikeVos){
            kgFusionService.createBaikeNode(baikeVo);
        }
        kgFusionService.createBaikeRelation((Map <String, BaikeDataVo>) resultItems.getAll().get("baikeRelationsMap"),resultItems.getAll().get("title").toString());
    }

}
