package com.zihao;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


/**
 * @author szq
 * Created on 2023-08-01
 * 使用Java+Selenium 自动框架实现
 */
public class Dongfang {

    public static void main(String[] args) {
        // 1、设置chrome浏览器驱动
        System.setProperty("webdriver.chrome.driver", "/Users/shenzhenqiang/Downloads/chromedriver-mac-arm64/chromedriver");

        // 2、设置ChromeOptions
        ChromeOptions chromeOptions = initChromeOptions();
        // 3、初始化一个浏览器实例
        WebDriver webDriver = initWebDriver(chromeOptions);
        // 4、加载网页程序（某度）
        webDriver.get("https://www.ceair.com/shopping?searchKey=JTdCJTIydHJhdmVsVHlwZSUyMiUzQSUyMm9uZXdheSUyMiUyQyUyMnBhc3Nlbmdlck51bSUyMiUzQSUyMjElMkMwJTJDMCUyMiUyQyUyMmRlcENpdHklMjIlM0ElMjJTSEElMjIlMkMlMjJhcnJDaXR5JTIyJTNBJTIyQkpTJTIyJTJDJTIyZGVwVmFsdWVzJTIyJTNBJTIyU0hBJTJDUFZHJTIyJTJDJTIyYXJyVmFsdWVzJTIyJTNBJTIyUEVLJTJDUEtYJTIyJTJDJTIyZGVwQ2l0eU5hbWUlMjIlM0ElMjIlRTQlQjglOEElRTYlQjUlQjclMjIlMkMlMjJhcnJDaXR5TmFtZSUyMiUzQSUyMiVFNSU4QyU5NyVFNCVCQSVBQyUyMiUyQyUyMmRhdGUlMjIlM0ElMjIyMDIzLTA4LTAyJTIyJTJDJTIyY2FiaW5DbGFzcyUyMiUzQSUyMkFMTCUyMiUyQyUyMnBheVdheSUyMiUzQSUyMm1vbmV5JTIyJTJDJTIyZGVwU2VsZWN0VmFsdWUlMjIlM0ElMjJTSEElMkNQVkclMjIlMkMlMjJhcnJTZWxlY3RWYWx1ZSUyMiUzQSUyMlBFSyUyQ1BLWCUyMiUyQyUyMmRlcExhYmVsJTIyJTNBJTIyJTIyJTJDJTIyYXJyTGFiZWwlMjIlM0ElMjIlMjIlMkMlMjJpc0FyckNpdHklMjIlM0F0cnVlJTJDJTIyaXNEZXBDaXR5JTIyJTNBdHJ1ZSUyQyUyMmRlcENuJTIyJTNBJTIyQ04lMjIlMkMlMjJ0JTIyJTNBMTY5MDk4ODU5ODk3OCU3RA%3D%3DENCODEKEY");

        List<WebElement> flightList = webDriver.findElements(By.className("shopping-thumb-inner-container flex-col shoppingThumb0 shopping-thumbnail-container can-click"));
        flightList.forEach(flight -> {
            WebElement flightLogo = flight.findElement(By.className("fl-logo"));
            // 航空公司
//            WebElement flightCompany = flightLogo.findElement(By.className("aircom"));
            System.out.println("东方航空");
            // 航班号
            WebElement flightNumber = flightLogo.findElement(By.className("flight-info-inner-container flex al-center border-right"));
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
