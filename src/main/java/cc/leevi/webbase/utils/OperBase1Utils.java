package cc.leevi.webbase.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class OperBase1Utils{
    /**
     * load cvs模式创建neo4j节点
     * @param filePath
     * @return
     */
    public static String lcCreateNodes(String filePath,String type){
        String lcCypher ="";
        /**right-被保险人*/
        if("n1".equals(type)||type=="n1"){
//            lcCypher = "USING PERIODIC COMMIT\n" +
              lcCypher =  "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                    "MERGE(:被保险人{name:line.c_Insured_Nme})";
        }
        /**right-保单号*/
        if("n2".equals(type)||type=="n2"){
//            lcCypher = "USING PERIODIC COMMIT\n" +
                    lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                    "MERGE(:保单号{name:line.c_Ply_No,保险起期:line.t_Insrnc_Bgn_Tm,保险止期:line.t_Insrnc_End_Tm})";
        }
        /**right-赔案号*/
        if("n3".equals(type)||type=="n3"){
//            lcCypher = "USING PERIODIC COMMIT\n" +
                    lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                    "MERGE(:赔案号{name:line.c_Clm_No,报案时间:line.t_Rpt_Tm,出险时间:line.t_Accdnt_Tm,赔付类型:line.c_Pay_Liability})";
        }
        /**right-收款人*/
        if("n4".equals(type)||type=="n4"){
//            lcCypher = "USING PERIODIC COMMIT\n" +
                    lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                    "MERGE(:收款人{name:line.c_Payee_Nme})";
        }
        /**right-银行卡*/
        if("n5".equals(type)||type=="n5"){
//            lcCypher = "USING PERIODIC COMMIT\n" +
                    lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                    "MERGE(:银行卡{name:line.c_Payee_No})";
        }
        /**right-报案电话*/
        if("n6".equals(type)||type=="n6"){
//            lcCypher = "USING PERIODIC COMMIT\n" +
                    lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                    "MERGE(:报案电话{name:line.c_Rptman_Tel})";
        }
        return lcCypher;
    }

    /**
     * load cvs模式创建neo4j节点
     * @param filePath
     * @param type
     */
    public static String lcCreateLines(String filePath,String type) {
        String lcCypher ="";
        /**L-1*/
        if("l1".equals(type)||type=="l1"){
//            lcCypher = "USING PERIODIC COMMIT\n" +
                    lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS row\n" +
                    "MATCH (aa:被保险人 {name:row.c_Insured_Nme}), (bb:保单号 {name:row.c_Ply_No}) \n" +
                    "MERGE (aa) -[:拥有保单]-> (bb)";
        }
        /**L-2*/
        if("l2".equals(type)||type=="l2"){
//            lcCypher = "USING PERIODIC COMMIT\n" +
                    lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS row\n" +
                    "MATCH (aa:保单号 {name:row.c_Ply_No}), (bb:赔案号 {name:row.c_Clm_No}) \n" +
                    "MERGE (aa) -[:发生赔案]-> (bb)";
        }

        /**L-3*/
        if("l3".equals(type)||type=="l3"){
//            lcCypher = "USING PERIODIC COMMIT\n" +
                    lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS row\n" +
                    "MATCH (aa:赔案号 {name:row.c_Clm_No}), (bb:报案电话 {name:row.c_Rptman_Tel}) \n" +
                    "MERGE (aa) -[:报案电话是]-> (bb)";
        }
        /**L-4*/
        if("l4".equals(type)||type=="l4"){
//            lcCypher = "USING PERIODIC COMMIT\n" +
                    lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS row\n" +
                    "MATCH (aa:赔案号 {name:row.c_Clm_No}), (bb:收款人 {name:row.c_Payee_Nme}) \n" +
                    "MERGE (aa) -[:赔款支付给]-> (bb)";
        }
        /**L-5*/
        if("l5".equals(type)||type=="l5"){
//            lcCypher = "USING PERIODIC COMMIT\n" +
                    lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS row\n" +
                    "MATCH (aa:收款人 {name:row.c_Payee_Nme}), (bb:银行卡 {name:row.c_Payee_No}) \n" +
                    "MERGE (aa) -[:银行卡是]-> (bb)";
        }
        return lcCypher;
    }
    public static String getAllNodeByName(String insuredName){
        String cypher = "match (a0:被保险人{name:'"+insuredName+"'})-[*]->(d0:收款人) with d0\n" +
                "match dat1=(a1:被保险人)-[*]->(c1:赔案号)-[cd1]->(d1:收款人)-[df1]->(f1:银行卡) ,dat2=(c1:赔案号)-[ce1]-(e1:报案电话) where d0.name= d1.name \n" +
                "return dat1 as daa,dat2 as dab\n" +
                "union\n" +
                "match (a2:被保险人{name:'"+insuredName+"'})-[*]->(e2:报案电话) with e2\n" +
                "match dat3=(a3:被保险人)-[*]->(c3:赔案号)-[cd3]->(d3:收款人)-[df3]->(f3:银行卡) ,dat4=(c3:赔案号)-[ce3]-(e3:报案电话) where e2.name= e3.name \n" +
                "return dat3 as daa,dat4 as dab";
        return cypher;
    }

    public static List<String> location(List<String> antVJsonList) {
        for(String avj:antVJsonList){
//            if(){
//
//            }

        }
        return antVJsonList;
    }

//    public static String getBase1StandJson(JSONObject inputJson,Integer base1NodeX,Integer base1NodeY) {
//        /**分流节点与边*/
//        if(inputJson.containsKey("_labels")){
//            String base1NodeStand = "'node', { id: '<id>', shape:'circle', label: '<name>',labelCfg: { position: 'bottom' },style: { fill: '#c27ba0', stroke: '#c27ba0' }, x: <x>, y: <y> }";
//            String nodeType = inputJson.get("_labels").toString();
//            String nodeValue = inputJson.get("name").toString();
//            String nodeId = inputJson.get("_id").toString();
//
//            if("[\"被保险人\"]".equals(nodeType)||nodeType=="[\"被保险人\"]"){
//                base1NodeStand = base1NodeStand.replace("<id>",nodeId).replace("<name>",nodeValue).replace("<x>",base1NodeX.toString()).replace("<y>",base1NodeY.toString());
//                System.out.println("------------values:"+base1NodeStand);
//            }
////            if(inputJson.containsValue("[\"保单号\"]")){
////                base1NodeStand = base1NodeStand.replace("<name>",inputJson.get("_labels").toString()).replace("<x>",base1NodeStandx.toString()).replace("<y>",base1NodeStandy.toString());
////            }
//            return base1NodeStand;
//        }else if(inputJson.containsKey("_type")){
//            String base1LineStand = "'edge', { id: 'edge0', source: '111', target: '222', label: '拥有保单',style: {endArrow: true,stroke: 'grey'},labelCfg: {autoRotate: true,style: {stroke: 'white',lineWidth: 5,fill: 'grey'}}}";
//            return base1LineStand;
//        }else{
//            return null;
//        }
//    }
}
