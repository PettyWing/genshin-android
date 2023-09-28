package com.youer.genshin.resp;

import java.util.List;

public class LoginResp {
    private List<RelicsDTO> relics;

    public List<RelicsDTO> getRelics() {
        return relics;
    }

    public void setRelics(List<RelicsDTO> relics) {
        this.relics = relics;
    }
}
