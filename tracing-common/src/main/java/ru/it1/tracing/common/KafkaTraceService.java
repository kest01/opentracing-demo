package ru.it1.tracing.common;

import brave.propagation.B3SingleFormat;
import brave.propagation.TraceContextOrSamplingFlags;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class KafkaTraceService {

    private final SpanService spanService;

    public void runInSpan(String spanName, Headers headers, Runnable fn) {
        TraceContextOrSamplingFlags traceContext = getTraceContext(headers);
        if (traceContext == null) {
            fn.run();
        } else {
            spanService.runInTraceContext(spanName, traceContext, () -> {
                fn.run();
                return null;
            });
        }
    }

    public static TraceContextOrSamplingFlags getTraceContext(Headers headers) {
        Header b3Header = headers.lastHeader("b3");
        return b3Header == null ? null : B3SingleFormat.parseB3SingleFormat(new String(b3Header.value(), StandardCharsets.UTF_8));
    }

}
