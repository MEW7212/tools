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