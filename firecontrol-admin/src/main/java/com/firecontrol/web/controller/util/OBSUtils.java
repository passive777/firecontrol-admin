package com.firecontrol.web.controller.util;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.ListBucketsRequest;
import com.obs.services.model.ObjectListing;
import com.obs.services.model.ObsBucket;
import com.obs.services.model.ObsObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * @ProjectName firecontrol
 * @Author 麦奇
 * @Email biaogejiushibiao@outlook.com
 * @Date 6/17/20 8:47 AM
 * @Version 1.0
 * @Title: OBSUtils
 * @Description: OBS文件工具类
 **/

public class OBSUtils {
    // 初始化OBS客户端需要的参数
    // 终端节点
    private static final String endPoint = "https://obs.cn-south-1.myhuaweicloud.com";
    // 访问钥匙(access key)
    private static final String ak = "0VNUCBN5AGUAHQKQMOFD";
    // 访问秘钥(secret key)
    private static final String sk = "52p8zsRpCb6WToIvtQDCD19qYYNDeOPQEM1t0oBq";
    // 桶名称
    private static final String g_bucketName = "obs-gxuwz1";
    // 可用区
    private static final String g_bucketLoc = "cn-south-1";


    public static String uploadToOBS(MultipartFile multipartFile) {


        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        ObsBucket obsBucket;
        String fileExt = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")).toLowerCase();
        String objectname = UUID.randomUUID().toString()+fileExt;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            // 1.创建一个桶
            obsBucket = createBucket(obsClient);
            // localfile为待上传的本地文件路径，需要指定到具体的文件名
            obsClient.putObject(obsBucket.getBucketName(), objectname, inputStream);
            //关闭连接
            obsClient.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return objectname;
    }

    public static InputStream downloadFromOBS(String objectname) {
        // 创建ObsClient实例
        final ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        //获取对象
        ObsObject obsObject = obsClient.getObject(g_bucketName, objectname);
        // 读取对象内容
        InputStream input = obsObject.getObjectContent();
        //re
        return input;
    }

    // 1.创建一个桶
    private static ObsBucket createBucket(ObsClient obsClient) {
        // 判断客户端是否已经存在该桶
        boolean exits = obsClient.headBucket(g_bucketName);
        // 若存在获取客户端上面的桶
        if (exits) {
            List<ObsBucket> list = obsClient.listBuckets(new ListBucketsRequest());
            list.removeIf(bucket -> !(bucket.getBucketName()).equals(g_bucketName));
            return list.get(0);
        }
        // 不存在则创建桶
        ObsBucket obsBucket = new ObsBucket(g_bucketName, g_bucketLoc);
        obsClient.createBucket(obsBucket);
        System.out.println("创建成功--" + g_bucketName);
        return obsBucket;
    }

    public static void main(String[] args) throws Exception{
//        String objectname = uploadToOBS(new File("/home/mikey/DATA/MIKEY/IDEAWorkSpace/firecontrol/firecontrol-admin/src/main/java/com/firecontrol/web/controller/util/OBSUtils.java"));
//        InputStream inputStream = downloadFromOBS(g_bucketName, objectname);
//        System.out.println(inputStream.available());
        System.out.println(UUID.randomUUID().toString());
    }
}