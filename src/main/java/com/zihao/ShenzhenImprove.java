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
public class ShenzhenImprove {

    public static void main(String[] args) throws Exception {


        // 1、设置chrome浏览器驱动
        System.setProperty("webdriver.chrome.driver", "/Users/shenzhenqiang/Downloads/chromedriver_mac_arm64/chromedriver");


        // 2、设置ChromeOptions
        ChromeOptions chromeOptions = initChromeOptions();
        chromeOptions.addArguments(
                "user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
        //        chromeOptions.addArguments("--headless");//无头浏览
        //        chromeOptions.addArguments("blink-settings=imagesEnabled=false");//禁用图片
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

        System.out.println("深圳航空");

        List<WebElement> flightList = webDriver.findElements(By.className("flightTr"));
        System.out.println(flightList.size());
        flightList.forEach(flight -> {
            String judge = flight.getAttribute("class");
            //            System.out.println(flight.getAttribute("class"));
            String id = flight.getAttribute("id");
            if (!judge.equals("flightTr")) {
                return;
            }
            List<WebElement> td = flight.findElements(By.xpath("./td"));
            //             航班号
            WebElement flightNumber1 = td.get(0).findElement(By.className("F20"));
            System.out.println("航班号:" + flightNumber1.getText());

            WebElement departTime = td.get(0).findElement(By.className("F22"));
            System.out.println("起飞落地时间: " + departTime.getText());

            // 起飞机场
            WebElement departAirport = td.get(0).findElement(By.className("F16"));
            try {
                // 出现中转不会异常
                td.get(0).findElement(By.className("F20")).findElement(By.className("tipsoTing")).findElement(By.className("routename"));

                System.out.println("机场起点: " + departAirport.getAttribute("row1-from"));
                // 如果有中转
                    System.out.println(departAirport.getAttribute("row2-info"));
                    System.out.println("机场终点：" + departAirport.getAttribute("row3-to"));

            }catch (Exception e) {
                System.out.println("机场起点: " + departAirport.getAttribute("row1-from"));
                // 如果没有中转
                System.out.println("机场终点： " + departAirport.getAttribute("row1-to"));

            }

            try {
                WebElement headPrice = td.get(1).findElement(By.className("F22"));
                System.out.println("头等舱价格:" + headPrice.getText());
                WebElement headSeatNumbers = td.get(1).findElement(By.className("F16"));
                System.out.println("头等舱座位:" + headSeatNumbers.getText());
            } catch (Exception e) {
                System.out.println("————————————没有头等舱——————————————");
            }
            try {
                WebElement superPrice = td.get(2).findElement(By.className("F22"));
                System.out.println("超值公务舱价格:" + superPrice.getText());
                WebElement superSeatNumbers = td.get(2).findElement(By.className("F16"));
                System.out.println("超值公务舱座位:" + superSeatNumbers.getText());
            } catch (Exception e) {
                System.out.println("————————————没有超值公务舱——————————————");
            }
            try {
                WebElement comfortablePrice = td.get(3).findElement(By.className("F22"));
                System.out.println("舒适经济舱价格:" + comfortablePrice.getText());
                WebElement comfortableSeatNumbers = td.get(3).findElement(By.className("F16"));
                System.out.println("舒适经济舱座位:" + comfortableSeatNumbers.getText());
            } catch (Exception e) {
                System.out.println("————————————没有舒适经济舱——————————————");
            }
            try {
                WebElement cheapPrice = td.get(4).findElement(By.className("F22"));
                System.out.println("经济舱价格:" + cheapPrice.getText());

                WebElement cheapSeatNumbers = td.get(4).findElement(By.className("F16"));
                System.out.println("经济舱座位:" + cheapSeatNumbers.getText());
            } catch (Exception e) {
                System.out.println("————————————没有经济舱——————————————");
            }
            System.out.println("-------------------------------------------------------------");

            //            Flight flightObject = Flight.builder()
            //                    .airCom(flightCompany.getText())
            //                    .flightNumber(flightNumber.getText())
            //                    .departTime(departTime.getText())
            //                    .departAirport(departAirport.getText())
            //                    .arriveTime(arriveTime.getText())
            //                    .arriveAirport(arriveAirport.getText())
            //                    .durationTime(durationTime.getText())
            //                    .price(priceNum.getText()).build();

            //            System.out.println(JsonUtil.toJSON(flightObject));

        });
        webDriver.close();


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
