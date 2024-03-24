package hello.typeconverter;

import hello.typeconverter.converter.IntegerToStringConverter;
import hello.typeconverter.converter.IpPortToStringConverter;
import hello.typeconverter.converter.StringIpPortConverter;
import hello.typeconverter.formatter.MyNumberFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    // 스프링 내부에서 COnversionService를 제공한다.
    // WebMvcConfigurer가 제공하는 addFormatters를 사용해서 추가하고 싶은 컨버터를 등록하면 된다.
    // 이렇게 하면 스프링 내부에서 사용하는 ConversionService에 컨버터를 추가해준다.
    @Override
    public void addFormatters(FormatterRegistry registry) {


//        registry.addConverter(new StringIpPortConverter());
//        registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new StringIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());

        // 추가
        registry.addFormatter(new MyNumberFormatter());
    }
}
