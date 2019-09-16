package cc.leevi.webbase.controller;

import cc.leevi.webbase.service.KgCommonService;
import cc.leevi.webbase.service.KgFusionService;
import cc.leevi.webbase.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static com.alibaba.fastjson.JSON.toJSON;

/**
 * base-1，数据融合
 * @author WangYingce
 *
 */
@RestController
@RequestMapping
public class FusionController {
    @Autowired
    private KgFusionService kgFusionService;
    @Autowired
    private KgCommonService kgCommonService;

    /**
     * loadCVS批量导数(推荐)
     * @param fileIndex
     * @throws Exception
     */
    @GetMapping("pumpClaimDataLc")
    public void pumpClaimDataLc(String fileIndex) throws Exception{
        System.out.println("ate-pumpClaimDataLc start at:"+DateUtils.dateToString(new Date(), 3));
//        String filePath = "http://10.0.0.25:9999/movie_graph/download/"+fileIndex;
        String filePath = "file:///"+fileIndex;
        kgFusionService.createModelsBase1(filePath);
        System.out.println("ate-pumpClaimDataLc end at:"+DateUtils.dateToString(new Date(), 3));
    }


    /**
     * loadCVS批量导数 - alpha
     * @param fileIndex
     * @throws Exception
     */
    @GetMapping("lcAlpha")
    public void lcAlpha(String fileIndex,String flag) throws Exception{
        System.out.println("ate-lcAlpha s:"+DateUtils.dateToString(new Date(), 3));
        System.out.println("ate-lcAlpha flag:"+flag);
        kgFusionService.cLcAlpha2("file:///"+fileIndex,flag);
        System.out.println("ate-lcAlpha e:"+DateUtils.dateToString(new Date(), 3));
    }



}
