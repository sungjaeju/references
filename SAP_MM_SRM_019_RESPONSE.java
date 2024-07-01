package com.example.ams.model.soap;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "returnStatus",
    "message",
    "etResult"
})
@XmlRootElement(name = "SAP_MM_SRM_019Response", namespace = "http://SAP_MM_SRM_019.xsdSAP_MM_SRM_019_WS")
public class SAP_MM_SRM_019_RESPONSE {
    @XmlElement(name = "RETURN")
    protected String returnStatus;
    @XmlElement(name = "MESSAGE")
    protected String message;
    @XmlElement(name = "ET_RESULT")
    protected List<EtResult> etResult;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "aufnr",
        "ktext",
        "auart",
        "autyp",
        "loekz",
        "kostl",
        "prctr",
        "pspel",
        "arbpl",
        "arbplNm",
        "kostlNm",
        "prctrNm",
        "gltrp",
        "gstrp",
        "erdat",
        "idat1",
        "idat2",
        "poski",
        "post1",
        "auartNm",
        "ilatx"
    })

    @Getter
    @Setter
    @ToString
    public static class EtResult {
        @XmlElement(name = "AUFNR")
        protected String aufnr;
        @XmlElement(name = "KTEXT")
        protected String ktext;
        @XmlElement(name = "AUART")
        protected String auart;
        @XmlElement(name = "AUTYP")
        protected String autyp;
        @XmlElement(name = "LOEKZ")
        protected String loekz;
        @XmlElement(name = "KOSTL")
        protected String kostl;
        @XmlElement(name = "PRCTR")
        protected String prctr;
        @XmlElement(name = "PSPEL")
        protected String pspel;
        @XmlElement(name = "ARBPL")
        protected String arbpl;
        @XmlElement(name = "ARBPL_NM")
        protected String arbplNm;
        @XmlElement(name = "KOSTL_NM")
        protected String kostlNm;
        @XmlElement(name = "PRCTR_NM")
        protected String prctrNm;
        @XmlElement(name = "GLTRP")
        protected String gltrp;
        @XmlElement(name = "GSTRP")
        protected String gstrp;
        @XmlElement(name = "ERDAT")
        protected String erdat;
        @XmlElement(name = "IDAT1")
        protected String idat1;
        @XmlElement(name = "IDAT2")
        protected String idat2;
        @XmlElement(name = "POSKI")
        protected String poski;
        @XmlElement(name = "POST1")
        protected String post1;
        @XmlElement(name = "AUART_NM")
        protected String auartNm;
        @XmlElement(name = "ILATX")
        protected String ilatx;
    }
}
