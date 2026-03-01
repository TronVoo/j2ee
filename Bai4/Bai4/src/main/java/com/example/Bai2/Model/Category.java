package com.example.Bai2.Model;

public enum Category {
    TIỂU_THUYẾT("Tiểu thuyết"),
    TRẺ_EM("Trẻ em"),
    KIẾN_THỨC("Kiến thức"),
    LỊCH_SỬ("Lịch sử"),
    KHOA_HỌC("Khoa học"),
    TÂM_LÝ("Tâm lý"),
    KINH_TẾ("Kinh tế"),
    SELF_HELP("Self-help");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
