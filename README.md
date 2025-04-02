#apps
- XmlSearchErrorTasks 找出 XML 有錯誤的部分
- GenL2DataTasks 現場下載設備資料再從我們產生 sql 這邊補到 l2
- GetNewBCTNumberFromNASTasks 根據 nas 上的資料找出資料表中是否有重複的 bct_number，
  若有重覆就跳過，無重覆就加入
- LaserSerialMapTasks 澎湖案雷雕號碼與設備編號的對應
- ModifyAndRewriteExcelTasks 修改或重寫 excel 的方法
- RotateImageTasks 旋轉圖片
- SpectrumCloseTasks 檢查誰有開水頻譜並列出介面編號寫成檔案
- WindowsServiceTasks 檢查 service 的狀態，若是關閉就重新開啟
- AddDsmPositionTask 把 nas 上建立的 DSM 點位建到 125.11
#changes
- 1.0.1
    - 新增 AddDsmPositionTask，祥龍在 nas 建立 DSM 點位，把 excel 抓下來，
      放到 ref 資料夾，透過 application 配置 excel 位置，執行程式跑到 local db
- 1.0.2
    - 讀檔新增 1800 大用水戶水號
- 1.0.3 
    - 做 AMR CLIENT 試打中華電信
- 1.0.4
    - 寫入表號 bct_number 對應到 excel
- 1.0.5
    - 計算瞬間流量
- 1.0.6
    - 抓取資料庫的所有有 interface_id 的表，並更換 interface_id
- 1.0.7
    - 按照檔名日期壓縮 json 檔案，並刪除
- 1.0.8
    - 人工計算補日結
- 1.0.9
    - 產生 BCP 的檔案
- 1.1.0
    - 產生 L2 的資料(DSM 補資料)
- 1.1.1
    - 從 nas 上的 excel 抓取 bct_number
- 1.1.2
    - 雷雕號碼與介面編號的對應建立並寫入資料庫
- 1.1.3
    - 改或重寫 excel 的方法
- 1.1.4
    - MRB 的加解密
- 1.1.5
    - 旋轉圖片
- 1.1.6
    - 送 UDP 封包到指定 IP
- 1.1.7
    - 關閉水頻譜
- 1.1.8
    - 檢查服務運行狀況，如果是 stop 就 restart
- 1.1.9
    - 檢查 XML 錯誤
- 1.2.0
    - 更換 interface_id 使用 sql 抓取有這個欄位的所有表
- 1.2.1
    - mango db 砍 collection
- 1.2.2
    - 通訊線撈資料
    - jet stream 自動重連
    - 送 UDP 封包
- 1.2.3
    - 做 DSM 驗收報告
    - 匯入 FOT2 的表
- 1.2.4
    - 做直飲台每月驗收報告
- 1.2.5
    - 直飲台每月驗收報告更改欄位
    - 修改日期欄位的顯示
- 1.2.6
    - 直飲台報表欄位新增，改寫成 xlsx，不使用 csv，所有點位用 sheet 分開不另外產生檔案