package com.zihao;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * @author zhangzhihao06 <zhangzhihao06@kuaishou.com>
 * Created on 2023-08-01
 */
public class BaiduImageSeleniumTest {


    public static void main(String[] args) {
        // 1、设置chrome浏览器驱动
        System.setProperty("webdriver.chrome.driver", "/Users/zhangzhihao/Downloads/chromedriver-mac-arm64/chromedriver");
        // 2、设置ChromeOptions
        ChromeOptions chromeOptions = initChromeOptions();
        // 3、初始化一个浏览器实例
        WebDriver webDriver = initWebDriver(chromeOptions);
        // 4、加载网页程序（某度）
        webDriver.get("https://image.baidu.com/");
        // 5、通过ID选择器定位输入框,并设置搜索关键字”帅哥“
        webDriver.findElement(By.id("kw")).sendKeys("帅哥");
        // 6、通过Class类样式选择器定位,模拟点击事件并提交表单
        webDriver.findElement(By.className("s_btn")).submit();
        // 7、加载第二个页面：展示搜索页面元素信息
        WebElement webElement = webDriver.findElement(By.id("imgid"));
        // 8、定位搜索到所有图片元素
        List<WebElement> imgs = webElement.findElements(By.className("imgbox"));
        // 9、异步获取图片地址
        syncThread(imgs);
//        // 10、模拟页面图片点击事件
//        imgs.forEach(images -> {
//            images.click();
//        });
    }


    private static ChromeOptions initChromeOptions() {
        ChromeOptions options = new ChromeOptions();
            /*// 启动时自动最大化窗口
            options.addArguments("--start-maximized");
            // 禁用阻止弹出窗口
            options.addArguments("--disable-popup-blocking");
            // 启动无沙盒模式运行
            options.addArguments("no-sandbox");
            // 禁用扩展
            options.addArguments("disable-extensions");
            // 默认浏览器检查
            options.addArguments("no-default-browser-check");
            // 设置chrome 无头模式
            options.setHeadless(Boolean.TRUE);
            //不用打开图形界面。
            options.addArguments("--headless");*/
        options.setBinary("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
        return options;
    }


    private static WebDriver initWebDriver(ChromeOptions options) {
        WebDriver webDriver = new ChromeDriver(options);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return webDriver;
    }

    public static void syncThread(List<WebElement> imgs) {
        Thread thrad = new Thread(new myThread(imgs));
        thrad.start();
    }

}


class myThread implements Runnable {
    List<WebElement> imgs;

    public myThread(List<WebElement> imgs) {
        this.imgs = imgs;
    }

    @Override
    public void run() {
        imgs.forEach(images -> {
            WebElement currentImages = images.findElement(By.className("main_img"));
            String src = currentImages.getAttribute("src");
            System.out.println("图片地址：->" + src);
            //TODO
        });
    }
}