package cc.leevi.webbase.pipeline;
import cc.leevi.webbase.service.KgFusionService;
import cc.leevi.webbase.support.SpringApplicationContextHolder;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
public class Neo4jSavePipeline implements Pipeline {
    private KgFusionService kgFusionService;

    private void ensureService(){
        if(kgFusionService == null){
            kgFusionService = SpringApplicationContextHolder.getBean(KgFusionService.class);
        }
    }

    public void process(ResultItems resultItems, Task task) {
        ensureService();
        System.out.println(resultItems.getAll());
        /**处理resultItems*/

        kgFusionService.createPumpIndex();
//        neo4jJdbcTemplate.update("CREATE (na:User{username:'wyc',name:'manager',role:'1'}) ");
    }
}
