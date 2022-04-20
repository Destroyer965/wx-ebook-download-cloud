package com.yangjiahai.ebookdownload;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.yangjiahai.ebookdownload.dto.OssDTO;
import org.bouncycastle.asn1.esf.SPuri;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.*;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/15 09:52
 */
@SpringBootTest
public class OssApplicationTest {
    @Resource
    private OssDTO uploadDTO;

    @Test
    public void test1() {
        System.out.println(uploadDTO);
    }

    @Test
    public void upload() {
        String endpoint = "https://oss-cn-chengdu.aliyuncs.com";
        String accessKeyId = "xxxx";
        String accessKeySecret = "xxx";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "yangjiahai";
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String filePath = "/Users/yangjiahai/desktop/test.txt";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String objectName = "test" + ".txt";
        try {
            InputStream inputStream = new FileInputStream(filePath);
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
        } catch (ClientException ce) {
            System.out.println("Error Message:" + ce.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    @Test
    public void downloadTest() {
        String endpoint = "https://oss-cn-chengdu.aliyuncs.com";
        String accessKeyId = "xxx";
        String accessKeySecret = "xxx";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "yangjiahai";
        // 填写不包含Bucket名称在内的Object完整路径，例如testfolder/exampleobject.txt。
        String objectName = "test/%E6%BC%AB%E7%94%BB%E7%AE%97%E6%B3%95%EF%BC%9A%E5%B0%8F%E7%81%B0%E7%9A%84%E7%AE%97%E6%B3%95%E4%B9%8B%E6%97%85.pdf";

        String pathName = "/Users/yangjiahai/desktop/数据结构.pdf";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 下载Object到本地文件，并保存到指定的本地路径中。如果指定的本地文件存在会覆盖，不存在则新建。
            // 如果未指定本地路径，则下载后的文件默认保存到示例程序所属项目对应本地路径中。
            ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(pathName));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    @Test
    public void urlDecode() throws UnsupportedEncodingException {
        String decode = URLDecoder.decode("%E6%94%BB%E5%9F%8E%E7%8B%AE%E8%AE%BA%E5%9D%9B_%E8%AE%A1%E7%AE%97%E6%9C%BA%E5%9C%A3%E7%BB%8F%E6%B7%B1%E5%85%A5%E7%90%86%E8%A7%A3%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%B3%BB%E7%BB%9F%EF%BC%88%E5%8E%9F%E4%B9%A6%E7%AC%AC%E4%BA%8C%E7%89%88%EF%BC%89", "utf-8");
        System.out.println(decode);
    }

    @Test
    public void pathTest() {
        String url = "https://wx-ebook-download.oss-cn-chengdu.aliyuncs.com/booklist/2022/03/15/d42c28bb-1.pdf";
        String[] split = url.split("\\.");
        System.out.println("split.length = " + split.length);
        String s =split[split.length-2]+ "."+ split[split.length-1];
        String substring = s.substring(4);
        System.out.println("substring = " + substring);

    }
}
