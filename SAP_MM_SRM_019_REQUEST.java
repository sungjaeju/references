package com.example.ams.model.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "isInput"
})
@XmlRootElement(name = "SAP_MM_SRM_019", namespace = "http://SAP_MM_SRM_019.xsdSAP_MM_SRM_019_WS")
public class SAP_MM_SRM_019_REQUEST {

    @XmlElement(name = "IS_INPUT", namespace = "")
    protected IsInput isInput;

    @Getter
    @Setter
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "aufnr",
        "ktext",
        "auart",
        "auartNm",
        "kostl",
        "kostlNm",
        "arbpl",
        "arbplNm",
        "pspnr",
        "posid",
        "poski",
        "gstrpFr",
        "gstrpTo",
        "stat"
    })
    public static class IsInput {

        @XmlElement(name = "AUFNR")
        protected String aufnr;
        @XmlElement(name = "KTEXT")
        protected String ktext;
        @XmlElement(name = "AUART")
        protected String auart;
        @XmlElement(name = "AUART_NM")
        protected String auartNm;
        @XmlElement(name = "KOSTL")
        protected String kostl;
        @XmlElement(name = "KOSTL_NM")
        protected String kostlNm;
        @XmlElement(name = "ARBPL")
        protected String arbpl;
        @XmlElement(name = "ARBPL_NM")
        protected String arbplNm;
        @XmlElement(name = "PSPNR")
        protected String pspnr;
        @XmlElement(name = "POSID")
        protected String posid;
        @XmlElement(name = "POSKI")
        protected String poski;
        @XmlElement(name = "GSTRP_FR")
        protected String gstrpFr;
        @XmlElement(name = "GSTRP_TO")
        protected String gstrpTo;
        @XmlElement(name = "STAT")
        protected String stat;
    }
}
