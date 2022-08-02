package ru.it1.tracing.common;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContextOrSamplingFlags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpanService {

    private final Tracer tracer;

    public String getCurrentTraceId() {
        Span span = tracer.currentSpan();
        if (span != null) {
            return span.context().traceIdString();
        }
        return null;
    }

    public void addTagToSpan(String tag, String message) {
        Span span = tracer.currentSpan();
        span.tag(tag, message);
    }

    public <T> T runInNewSpan(String spanName, Supplier<T> func, Map<String, String> tags) {
        Span newSpan = tracer.nextSpan().name(spanName);
        tags.forEach(newSpan::tag);
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(newSpan.start())) {
            return func.get();
        } finally {
            newSpan.finish();
        }
    }

    public <T> T runInTraceContext(String spanName, TraceContextOrSamplingFlags context, Supplier<T> func) {
        Span newSpan = tracer.nextSpan(context).name(spanName);
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(newSpan.start())) {
            return func.get();
        } finally {
            newSpan.finish();
        }
    }

}
