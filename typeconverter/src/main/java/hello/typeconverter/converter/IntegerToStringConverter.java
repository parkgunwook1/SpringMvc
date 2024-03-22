package hello.typeconverter.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class IntegerToStringConverter implements Converter<Integer, String> {

    // 숫자를 문자로 변환하는 타입 컨버터, SpringToInterger와 반대의 일을 한다.
    @Override
    public String convert(Integer source) {
        log.info("converter source={}" , source);
        return String.valueOf(source);
    }
}
