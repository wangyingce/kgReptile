package cc.leevi.webbase.utils;

public class OperAlphaUtils {
    /**
     * load cvs模式创建neo4j节点
     * @param filePath
     * @return
     */
    public static String a1CNodes(String filePath,String type){
        String lcCypher ="";
        if("insNme".equals(type)||type=="insNme"){
            lcCypher =  "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                    "MERGE(:被保险人{name:line.c_Insured_Nme,证件号:line.c_Cert_No})";
        }
        if("rptNme".equals(type)||type=="rptNme"){
            lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                    "MERGE(:报案人{name:line.c_Rptman_Nme})";
        }
        if("rptTel".equals(type)||type=="rptTel"){
            lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                    "MERGE(:报案电话{name:line.c_Rptman_Tel})";
        }
        if("payNme".equals(type)||type=="payNme"){
            lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                    "MERGE(:收款人{name:line.c_Payee_Nme})";
        }
        if("payNo".equals(type)||type=="payNo"){
            lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                    "MERGE(:银行卡{name:line.c_Payee_No})";
        }
        return lcCypher;
    }

    /**
     * load cvs模式创建neo4j节点
     * @param filePath
     * @param type
     */
    public static String a1CRelationshipLines(String filePath,String type) {
        String lcCypher ="";

        if("ins_rN".equals(type)||type=="ins_rN"){
            lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS row\n" +
                    "MATCH (aa:被保险人 {name:row.c_Insured_Nme}), (bb:报案人 {name:row.c_Rptman_Nme}) \n" +
                    "MERGE (aa) -[:的报案人是]-> (bb)";
        }

        if("rN_rT".equals(type)||type=="rN_rT"){
            lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS row\n" +
                    "MATCH (aa:报案人 {name:row.c_Rptman_Nme}), (bb:报案电话 {name:row.c_Rptman_Tel}) \n" +
                    "MERGE (aa) -[:的报案电话是]-> (bb)";
        }

        if("ins_pNme".equals(type)||type=="ins_pNme"){
                    lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS row\n" +
                    "MATCH (aa:被保险人 {name:row.c_Insured_Nme}), (bb:收款人 {name:row.c_Payee_Nme}) \n" +
                    "MERGE (aa) -[:的赔款支付给]-> (bb)";
        }

        if("pNme_pNo".equals(type)||type=="pNme_pNo"){
            lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS row\n" +
                    "MATCH (aa:收款人 {name:row.c_Payee_Nme}), (bb:银行卡 {name:row.c_Payee_No}) \n" +
                    "MERGE (aa) -[:的银行卡是]-> (bb)";
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

    public static String getAllDatas() {
        String cypher = "match dat1=(a1:被保险人)-[*]->(c1:赔案号)-[cd1]->(d1:收款人)-[df1]->(f1:银行卡) ,dat2=(c1:赔案号)-[ce1]-(e1:报案电话) \n" +
                "return dat1 as daa,dat2 as dab\n";
        return cypher;
    }

    public static String cLcAlpha2Nodes(String filePath, String type,String flag) {
        String lcCypher ="";
        if("c_Insured_Nme".equals(type)||type=="c_Insured_Nme") {
            lcCypher = "LOAD CSV WITH HEADERS FROM '" + filePath + "' AS  line \n" +
                    "MERGE(:被保人{name:line.c_Insured_Nme,证件号:line.c_Cert_No})";
        }else if("c_Ply_No".equals(type)||type=="c_Ply_No") {
            lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                    "MERGE(:保单{name:line.c_Ply_No,保险起期:line.t_Insrnc_Bgn_Tm,保险止期:line.t_Insrnc_End_Tm})";
        }else if("c_Clm_No".equals(type)||"c_Clm_No"==type){
            if("auto".equals(flag)||"auto"==flag){
                lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                        "MERGE(:赔案{name:line.c_Clm_No,报案时间:line.t_Rpt_Tm,出险时间:line.t_Accdnt_Tm,赔付类型:line.c_Pay_Liability})";
            }else{
                lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                        "MERGE(:赔案{name:line.c_Clm_No,报案时间:line.t_Rpt_Tm,出险时间:line.t_Accdnt_Tm,赔付类型:line.c_Pay_Liability,保单号:line.c_Ply_No,保险起期:line.t_Insrnc_Bgn_Tm,保险止期:line.t_Insrnc_End_Tm})";
            }
        }else if("c_Payee_Nme".equals(type)||type=="c_Payee_Nme"){
            lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                    "MERGE(:接赔人{name:line.c_Payee_Nme})";
        }else if("c_Payee_No".equals(type)||type=="c_Payee_No"){
            lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                    "MERGE(:账号{name:line.c_Payee_No})";
        }else if("c_Rptman_Nme".equals(type)||type=="c_Rptman_Nme"){
            lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                    "MERGE(:报案人{name:line.c_Rptman_Nme})";
        }else if("c_Rptman_Tel".equals(type)||type=="c_Rptman_Tel"){
            lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS  line \n" +
                    "MERGE(:报案电话{name:line.c_Rptman_Tel})";
        }
        return lcCypher;
    }

    public static String cLcAlpha2RelationshipLines(String filePath, String type,String flag) {
        String lcCypher ="";
        if("c_Insured_Nme2c_Ply_No".equals(type)||type=="c_Insured_Nme2c_Ply_No") {
            lcCypher = "LOAD CSV WITH HEADERS FROM '" + filePath + "' AS row\n" +
                    "MATCH (aa:被保人 {name:row.c_Insured_Nme,证件号:row.c_Cert_No}), (bb:保单 {name:row.c_Ply_No}) \n" +
                    "MERGE (aa) -[:购买]-> (bb)";
        }else if("c_Ply_No2c_Clm_No".equals(type)||type=="c_Ply_No2c_Clm_No"){
            lcCypher = "LOAD CSV WITH HEADERS FROM '" + filePath + "' AS row\n" +
                    "MATCH (aa:保单 {name:row.c_Ply_No}), (bb:赔案 {name:row.c_Clm_No}) \n" +
                    "MERGE (aa) -[:出险]-> (bb)";
        }else if("c_Insured_Nme2c_Clm_No".equals(type)||type=="c_Insured_Nme2c_Clm_No"){
            lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS row\n" +
                    "MATCH (aa:被保人 {name:row.c_Insured_Nme,证件号:row.c_Cert_No}), (bb:赔案 {name:row.c_Clm_No}) \n" +
                    "MERGE (aa) -[:出险]-> (bb)";
        }else if("c_Clm_No2c_Payee_Nme".equals(type)||type=="c_Clm_No2c_Payee_Nme"){
            lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS row\n" +
                    "MATCH (aa:赔案 {name:row.c_Clm_No}), (bb:接赔人 {name:row.c_Payee_Nme}) \n" +
                    "MERGE (aa) -[:支付]-> (bb)";
        }else if("c_Payee_Nme2c_Payee_No".equals(type)||type=="c_Payee_Nme2c_Payee_No"){
            lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS row\n" +
                    "MATCH (aa:接赔人 {name:row.c_Payee_Nme}), (bb:账号 {name:row.c_Payee_No}) \n" +
                    "MERGE (aa) -[:持有]-> (bb)";
        }else if("c_Clm_No2c_Rptman_Nme".equals(type)||type=="c_Clm_No2c_Rptman_Nme"){
            lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS row\n" +
                    "MATCH (aa:赔案 {name:row.c_Clm_No}), (bb:报案人 {name:row.c_Rptman_Nme}) \n" +
                    "MERGE (aa) -[:报案人是]-> (bb)";
        }else if("c_Rptman_Nme2c_Rptman_Tel".equals(type)||type=="c_Rptman_Nme2c_Rptman_Tel"){
            lcCypher = "LOAD CSV WITH HEADERS FROM '"+filePath+"' AS row\n" +
                    "MATCH (aa:报案人 {name:row.c_Rptman_Nme}), (bb:报案电话 {name:row.c_Rptman_Tel}) \n" +
                    "MERGE (aa) -[:报案电话是]-> (bb)";
        }
        return lcCypher;
    }
}
