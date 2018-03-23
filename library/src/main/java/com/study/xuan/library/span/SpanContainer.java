package com.study.xuan.library.span;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : xuan.
 * Date : 18-3-23.
 * Description : the file description
 */
public class SpanContainer {
    public List<Object> spans;
    public int start;
    public int end;
    public int flag;

    public SpanContainer(List<Object> spans, int start, int end, int flag) {
        this.spans = spans;
        this.start = start;
        this.end = end;
        this.flag = flag;
    }

    public SpanContainer(Object spans, int start, int end, int flag) {
        this.spans = new ArrayList<>();
        this.spans.add(spans);
        this.start = start;
        this.end = end;
        this.flag = flag;
    }
}
