package com.example.kusitmshackthon.domain.healthlog.standard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StandardHealthLog {
    // 15세 여아 표준 영양 정보
    Float protein = 45F; // 단백질(g)
    Float calcium = 800F; // 칼슘(mg)
    Float sodium = 1.5F; // 나트륨(mg)
    Float fe = 17F; // 철(mg)
    Float zinc = 9F; // 아연(mg)
}
