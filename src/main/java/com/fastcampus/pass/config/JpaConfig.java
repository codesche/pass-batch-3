package com.fastcampus.pass.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing      // JPA auditing을 활성화 한다. entity의 생성일시와 수정일시를 자동화하는 용도로 사용 (시간을 자동으로 입력)
@Configuration
public class JpaConfig {

}
