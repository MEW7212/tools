package com.example.tools.tasks;

import lombok.extern.log4j.Log4j2;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Log4j2
@Component
public class XmlSearchErrorTasks {
    public void run() {
        File folder = new File("C:\\Users\\10043\\Desktop\\xmp_test_folder");
        String interface_id = "131081005623";  // 填入介面號尋找
        int i = 0;
        boolean interfaceNoEqual = true;
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                i++;
                //log.info("{}, {}", i, file.getName());
                try {
                    File inputFile = new File("C:\\Users\\10043\\Desktop\\xmp_test_folder\\" + file.getName());
                    SAXBuilder saxBuilder = new SAXBuilder();
                    Document document = saxBuilder.build(inputFile);
                    //log.info("root element :" + document.getRootElement().getName());
                    Element interfaceNoElement = document.getRootElement();
                    List<Element> interfaceNoList = interfaceNoElement.getChildren();

                    for (int temp = 0; temp < interfaceNoList.size(); temp++) {
                        Element interfaceIdNo = interfaceNoList.get(temp);
                        //log.info("Current Element :" + interfaceIdNo.getName());
                        Attribute attribute = interfaceIdNo.getAttribute("no");
                        if (interface_id.equals(attribute.getValue())) {
                            if(interfaceNoEqual){
                                log.info("interfaceNo : " + attribute.getValue());
                                interfaceNoEqual = false;
                            }
                            log.info("recDateTime : " + interfaceIdNo.getChild("interfaceSubSeq").getChild(
                                    "recDateTime").getText() + "  " + "{}, {}", i, file.getName());
                        }
                    }

                } catch (JDOMException e) {
                    log.error("jdom exception : ", e);
                } catch (IOException e) {
                    log.error("io exception : ", e);
                }
            }
        }
        log.info("total file : {} check success", i);
    }
}
