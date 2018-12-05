package com.bobongmd.app.a1cytewbcdifferentialcounter;

public class AppConstants {
    static final BloodCell ATYPICAL_LYMPHOCYTE = new BloodCell("ATYPICAL LYMPHOCYTE","A.T.L.");
    static final BloodCell BAND = new BloodCell("BAND","BAND");
    static final BloodCell BASOPHIL = new BloodCell("BASOPHIL","bayso");
    static final BloodCell EOSINOPHIL = new BloodCell("EOSINOPHIL","EOS");
    static final BloodCell LYMPHOBLAST = new BloodCell("LYMPHOBLAST","LYMPHOBLAST");
    static final BloodCell LYMPHOCYTE = new BloodCell("LYMPHOCYTE","LYMPH");
    static final BloodCell METAMYELOCYTE = new BloodCell("METAMYELOCYTE","META");
    static final BloodCell MONOCYTE = new BloodCell("MONOCYTE","MONO");
    static final BloodCell MYELOBLAST = new BloodCell("MYELOBLAST","myeloblast");
    static final BloodCell MYELOCYTE = new BloodCell("MYELOCYTE","MYELO");
    static final BloodCell NEUTROPHIL = new BloodCell("NEUTROPHIL","SEG");
    static final BloodCell NRBC = new BloodCell("NRBC","neucs");
    static final BloodCell PROMYELOCYTE = new BloodCell("PROMYELOCYTE","promyelo");

    static final BloodCell[] BLOOD_CELLS = new BloodCell[]{
            ATYPICAL_LYMPHOCYTE,
            BAND,
            BASOPHIL,
            EOSINOPHIL,
            LYMPHOBLAST,
            LYMPHOCYTE,
            METAMYELOCYTE,
            MONOCYTE,
            MYELOBLAST,
            MYELOCYTE,
            NEUTROPHIL,
            NRBC,
            PROMYELOCYTE
    };

}
