# coding=utf-8
# @Time    : 2023/8/5--10:32
# @Author  : child_lyj
# @File    : main
# @Software: PyCharm
# @Project : Spider
# @Description:
import time

from selenium import webdriver
from selenium.webdriver.common.by import By


options = webdriver.ChromeOptions()
user_agent='user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36'
options.add_argument(f'user-agent={user_agent}')
driver = webdriver.Chrome(options=options)
driver.get("https://www.shenzhenair.com/szair_B2C/")

time.sleep(3)
element = driver.find_element(By.CLASS_NAME, "layui-layer-btn1")  # 我知道了按钮
# print(element)
element.click()  # 点击关闭弹窗
element = driver.find_element(By.ID, "orgCity")  # 始发地输入框
# print(element)
element.send_keys("北京首都")
element = driver.find_element(By.ID, "dstCity")  # 终到地输入框
# print(element)
element.send_keys("深圳")
element = driver.find_element(By.ID, "orgDate")  # 触发日期输入框
# print(element)
element.clear()
element.send_keys("2023-08-14")
element = driver.find_element(By.CLASS_NAME, "ydxc")  # 预订行程按钮，用于隐藏日期选择框，否则会挡住查询按钮
element.click()
element = driver.find_element(By.ID, "search-flight")  # 立即预订查询按钮
# print(element)
element.click()
elements = driver.find_elements(By.CLASS_NAME, "flightTr")
# print(f"所有的信息:\n{elements}")

# 循环拿到每一趟航班的信息
for one_info in elements:
    # print(one_info)
    tds = one_info.find_elements(By.XPATH, "./td")  # 一趟航班所有的信息都放在6个<td>标签里面
    # print(tds)
    # print(len(tds))
    print(f"航班号: {tds[0].find_element(By.CLASS_NAME, 'F20').text}")  # 航班号
    print(f"航班时间: {tds[0].find_element(By.CLASS_NAME, 'F22').text}")  # 航班时间

    # 是否有中转
    is_transfer = False
    try:
        transfer_city = tds[0].find_element(By.CLASS_NAME, 'F20').find_element(By.CLASS_NAME, 'tipsoTing').get_attribute('routename') # 存在中转城市，则不会出现异常
        print(f"起点机场信息: {tds[0].find_element(By.CLASS_NAME, 'F16').get_attribute('row1-from')}")  # 机场信息
        print(f"中转机场信息: {tds[0].find_element(By.CLASS_NAME, 'F16').get_attribute('row1-to')}")  # 机场信息
        print(f"终点机场信息: {tds[0].find_element(By.CLASS_NAME, 'F16').get_attribute('row3-to')}")  # 机场信息
        is_transfer = True
    except:
        print(f"起点机场信息: {tds[0].find_element(By.CLASS_NAME, 'F16').get_attribute('row1-from')}")  # 机场信息
        print(f"中转机场信息: 无中转")  # 机场信息
        print(f"终点机场信息: {tds[0].find_element(By.CLASS_NAME, 'F16').get_attribute('row1-to')}")  # 机场信息
    try:
        print(f"头等/公务舱: {tds[1].find_element(By.CLASS_NAME, 'F22').text}, {tds[1].find_element(By.CLASS_NAME, 'F16').text}")  # 价格及座位信息
    except:
        print(f"头等/公务舱: 无该票务信息")
    try:
        print(f"超值公务舱: {tds[2].find_element(By.CLASS_NAME, 'F22').text}, {tds[2].find_element(By.CLASS_NAME, 'F16').text}")  # 价格及座位信息
    except:
        print(f"超值公务舱: 无该票务信息")
    try:
        print(f"舒适经济舱: {tds[3].find_element(By.CLASS_NAME, 'F22').text}, {tds[3].find_element(By.CLASS_NAME, 'F16').text}")  # 价格及座位信息
    except:
        print(f"舒适经济舱: 无该票务信息")
    try:
        print(f"经济舱: {tds[4].find_element(By.CLASS_NAME, 'F22').text}, {tds[4].find_element(By.CLASS_NAME, 'F16').text}")  # 价格及座位信息
    except:
        print(f"经济舱: 无该票务信息")

    print("-" * 80)  # 每一趟航班之间个分隔符
driver
