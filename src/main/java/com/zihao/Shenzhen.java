package com.zihao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


/**
 * @author szq
 * Created on 2023-08-01
 * 使用Java+Selenium 自动框架实现
 */
public class Shenzhen {

    public void method(){
        // 设置 ChromeDriver 路径
        System.setProperty("webdriver.chrome.driver", "/Users/shenzhenqiang/Downloads/chromedriver_mac_arm64 (2)/chromedriver");
        // 创建 ChromeOptions 对象，用于设置请求头和 Cookie
        ChromeOptions options = new ChromeOptions();
        // 添加请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("Connection", "keep-alive");
        headers.put("Host", "www.shenzhenair.com");
        headers.put("Referer", "https://www.shenzhenair.com/szair_B2C/flightsearch.action?orgCityCode=PEK&dstCityCode=SZX&hcType=DC&orgCity=%E5%8C%97%E4%BA%AC%E9%A6%96%E9%83%BD&dstCity=%E6%B7%B1%E5%9C%B3&orgDate=2023-08-08&dstDate=2023-08-09&easyFlyFlag=0&constId=%24%7BsearchCondition.constId%7D");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");



        options.addArguments("user-agent=" + headers.get("User-Agent"));


    }
    public static void main(String[] args) throws InterruptedException {
        // 1、设置chrome浏览器驱动
        System.setProperty("webdriver.chrome.driver", "/Users/shenzhenqiang/Downloads/chromedriver_mac_arm64/chromedriver");

        // 2、设置ChromeOptions
        ChromeOptions chromeOptions = initChromeOptions();
        chromeOptions.addArguments("User-Agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
        // 3、初始化一个浏览器实例
        WebDriver webDriver = initWebDriver(chromeOptions);
//        chromeOptions.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36,"
//                + "--cookie=");


//         4、加载网页程序（某度）
        webDriver.get("https://www.shenzhenair.com/szair_B2C");
        //*[@id="orgCity"]

        WebElement wait = webDriver.findElement(By.className("layui-layer-btn1"));
        wait.click();
        WebElement orgCity = webDriver.findElement(By.id("orgCity"));
        WebElement dstCity = webDriver.findElement(By.id("dstCity"));
        WebElement orgDate = webDriver.findElement(By.id("orgDate"));

        String orgCit = "北京首都";
        String dstCit = "深圳";

        orgCity.sendKeys(orgCit);
        orgDate.clear();
        orgDate.sendKeys("2023-08-16");

        dstCity.sendKeys(dstCit);



        WebElement submitButton = webDriver.findElement(By.id("search-flight"));
        submitButton.click();
        List<WebElement> flightList = webDriver.findElements(By.className("tblRouteList"));
        flightList.forEach(flight -> {
            WebElement flightLogo = flight.findElement(By.className("fl-logo"));
            // 航空公司
//            WebElement flightCompany = flightLogo.findElement(By.className("aircom"));
            System.out.println("深圳航空");
            // 航班号
            WebElement flightNumber = flightLogo.findElement(By.className("F20"));
            System.out.println(flightNumber);

            WebElement flDepart = flight.findElement(By.className("F22"));
            // 起飞时间
            WebElement departTime = flDepart.findElement(By.className("F22"));
            System.out.println(departTime);
            // 起飞机场
            WebElement departAirport = flDepart.findElement(By.className("airport"));
            System.out.println(departAirport);


            WebElement flArrive = flight.findElement(By.className("fl-arrive"));
            // 到达时间
            WebElement arriveTime = flArrive.findElement(By.className("hours")).findElement(By.cssSelector("span"));
            System.out.println(arriveTime);
            // 到达机场
            WebElement arriveAirport = flArrive.findElement(By.className("airport"));
            System.out.println(arriveAirport);

            WebElement flCenter = flight.findElement(By.className("fl-center"));
            // 飞行时间
            WebElement durationTime = flCenter.findElement(By.className("durationTime"));
            System.out.println(durationTime);

            WebElement price = flight.findElement(By.className("price"));
            // 价格
            WebElement priceNum = price.findElement(By.className("num"));
            System.out.println(priceNum);


            Flight flightObject = Flight.builder()
//                    .airCom(flightCompany.getText())
                    .flightNumber(flightNumber.getText())
                    .departTime(departTime.getText())
                    .departAirport(departAirport.getText())
                    .arriveTime(arriveTime.getText())
                    .arriveAirport(arriveAirport.getText())
                    .durationTime(durationTime.getText())
                    .price(priceNum.getText()).build();

            System.out.println(JsonUtil.toJSON(flightObject));

        });

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
}
