package cc.leevi.webbase.service;

import cc.leevi.webbase.utils.*;
import cc.leevi.webbase.vo.PumpClaimMapVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

@Service
@Transactional
public class KgFusionService {

    @Autowired
    @Qualifier("neo4jJdbcTemplate")
    protected JdbcTemplate neo4jJdbcTemplate;

    HashMap<String, String> linesMap = new HashMap<String, String>();
    PumpClaimMapVo initlaimMapVo =  new PumpClaimMapVo();

    /**
     * 创建抽水泵的索引，KG-base-1
     */
    public void createPumpIndex() {
        try {
            //如果查询不到管理账号则说明未有数据，手动创建索引与管理员账号
//            List<Map<String, Object>> findList = neo4jJdbcTemplate.queryForList("MATCH (na:User) where na.username='admin' return na");
//            if(findList==null||findList.size()<=0){
                // 创建索引
//                neo4jJdbcTemplate.update("CREATE INDEX ON :被保险人(name)");
//                neo4jJdbcTemplate.update("CREATE INDEX ON :保单号(name)");
//                neo4jJdbcTemplate.update("CREATE INDEX ON :赔案号(name)");
//                neo4jJdbcTemplate.update("CREATE INDEX ON :收款人(name)");
//                neo4jJdbcTemplate.update("CREATE INDEX ON :银行卡(name)");
//                neo4jJdbcTemplate.update("CREATE INDEX ON :报案电话(name)");
//                neo4jJdbcTemplate.update("CREATE INDEX ON :User(name)");
//                neo4jJdbcTemplate.update("CREATE INDEX ON :User(username)");
                neo4jJdbcTemplate.update("CREATE (na:User{username:'wyc',name:'管理员',role:'1',password:{1}}) ", MD5Utils.MD5Encode("admin", "UTF-8"));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建KG-base-1节点关系，连线
     * @param node1Name：节点2名
     * @param node1Value：节点1值
     * @param lineName：线说明
     * @param node2Name：节点2名
     * @param node2Value：节点2值
     */
    private void createKGLine(String node1Name,String node1Value,String lineName,String node2Name,String node2Value){
        if(node1Value==null||"".equals(node1Value)||"NA".equals(node1Value)){
            node1Value = "空"+node1Name;
        }
        if(node2Value==null||"".equals(node2Value)||"NA".equals(node2Value)){
            if(node2Name.endsWith("时间")){
                node2Value = "1999-01-01 00:00:00";
            }
            node2Value = "空"+node2Name;
        }
        linesMap.put("MATCH (aa:"+node1Name+" {name:'" + node1Value + "'}), (bb:"+node2Name+" {name:'" + node2Value
                + "'}) MERGE (aa) -[:"+lineName+"{name:''}]-> (bb)", "");
    }

    /***
     * loadCVS批量导数
     * @param filePath：cvs文件路径
     */
    public void createModelsBase1(String filePath) {
        System.out.println("creating nodes start at:"+DateUtils.dateToString(new Date(), 3));
        /**load cvs to creating nodes*/
        neo4jJdbcTemplate.update(OperBase1Utils.lcCreateNodes(filePath,"n1"));
        neo4jJdbcTemplate.update(OperBase1Utils.lcCreateNodes(filePath,"n2"));
        neo4jJdbcTemplate.update(OperBase1Utils.lcCreateNodes(filePath,"n3"));
        neo4jJdbcTemplate.update(OperBase1Utils.lcCreateNodes(filePath,"n4"));
        neo4jJdbcTemplate.update(OperBase1Utils.lcCreateNodes(filePath,"n5"));
        neo4jJdbcTemplate.update(OperBase1Utils.lcCreateNodes(filePath,"n6"));
        System.out.println("writing lines start at:"+DateUtils.dateToString(new Date(), 3));
        /**load cvs to writing lines*/
        neo4jJdbcTemplate.update(OperBase1Utils.lcCreateLines(filePath,"l1"));
        neo4jJdbcTemplate.update(OperBase1Utils.lcCreateLines(filePath,"l2"));
        neo4jJdbcTemplate.update(OperBase1Utils.lcCreateLines(filePath,"l3"));
        neo4jJdbcTemplate.update(OperBase1Utils.lcCreateLines(filePath,"l4"));
        neo4jJdbcTemplate.update(OperBase1Utils.lcCreateLines(filePath,"l5"));
    }

    public void cModelA1(String s) {
//        neo4jJdbcTemplate.update(OperAlpha1Utils.cNodes(s,"cma1_n1"));
//        neo4jJdbcTemplate.update(OperAlpha1Utils.cNodes(s,"cma1_n2"));
//        neo4jJdbcTemplate.update(OperAlpha1Utils.cNodes(s,"cma1_n3"));
//        neo4jJdbcTemplate.update(OperAlpha1Utils.cRelationshipLines(s,"cma1_r1"));
//        neo4jJdbcTemplate.update(OperAlpha1Utils.cRelationshipLines(s,"cma1_r2"));
    }

    public void cLcAlpha(String s) {
        neo4jJdbcTemplate.update(OperAlphaUtils.a1CNodes(s,"insNme"));
        neo4jJdbcTemplate.update(OperAlphaUtils.a1CNodes(s,"rptNme"));
        neo4jJdbcTemplate.update(OperAlphaUtils.a1CNodes(s,"rptTel"));
        neo4jJdbcTemplate.update(OperAlphaUtils.a1CNodes(s,"payNme"));
        neo4jJdbcTemplate.update(OperAlphaUtils.a1CNodes(s,"payNo"));
        neo4jJdbcTemplate.update(OperAlphaUtils.a1CRelationshipLines(s,"ins_rN"));
        neo4jJdbcTemplate.update(OperAlphaUtils.a1CRelationshipLines(s,"rN_rT"));
        neo4jJdbcTemplate.update(OperAlphaUtils.a1CRelationshipLines(s,"ins_pNme"));
        neo4jJdbcTemplate.update(OperAlphaUtils.a1CRelationshipLines(s,"pNme_pNo"));
    }


    public void cLcAlpha2(String filePath,String flag) {
        neo4jJdbcTemplate.update(OperAlphaUtils.cLcAlpha2Nodes(filePath,"c_Insured_Nme",flag));
        if("auto"==flag||"auto".equals(flag)){
            neo4jJdbcTemplate.update(OperAlphaUtils.cLcAlpha2Nodes(filePath,"c_Ply_No",flag));
        }
        neo4jJdbcTemplate.update(OperAlphaUtils.cLcAlpha2Nodes(filePath,"c_Clm_No",flag));
        neo4jJdbcTemplate.update(OperAlphaUtils.cLcAlpha2Nodes(filePath,"c_Payee_Nme",flag));
        neo4jJdbcTemplate.update(OperAlphaUtils.cLcAlpha2Nodes(filePath,"c_Payee_No",flag));
        neo4jJdbcTemplate.update(OperAlphaUtils.cLcAlpha2Nodes(filePath,"c_Rptman_Nme",flag));
        neo4jJdbcTemplate.update(OperAlphaUtils.cLcAlpha2Nodes(filePath,"c_Rptman_Tel",flag));
        if("auto"==flag||"auto".equals(flag)) {
            neo4jJdbcTemplate.update(OperAlphaUtils.cLcAlpha2RelationshipLines(filePath,"c_Insured_Nme2c_Ply_No",flag));
            neo4jJdbcTemplate.update(OperAlphaUtils.cLcAlpha2RelationshipLines(filePath,"c_Ply_No2c_Clm_No",flag));
        }else{
            neo4jJdbcTemplate.update(OperAlphaUtils.cLcAlpha2RelationshipLines(filePath,"c_Insured_Nme2c_Clm_No",flag));
        }
        neo4jJdbcTemplate.update(OperAlphaUtils.cLcAlpha2RelationshipLines(filePath,"c_Clm_No2c_Payee_Nme",flag));
        neo4jJdbcTemplate.update(OperAlphaUtils.cLcAlpha2RelationshipLines(filePath,"c_Payee_Nme2c_Payee_No",flag));
        neo4jJdbcTemplate.update(OperAlphaUtils.cLcAlpha2RelationshipLines(filePath,"c_Clm_No2c_Rptman_Nme",flag));
        neo4jJdbcTemplate.update(OperAlphaUtils.cLcAlpha2RelationshipLines(filePath,"c_Rptman_Nme2c_Rptman_Tel",flag));
    }
}
