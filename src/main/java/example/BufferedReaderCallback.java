package example;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderCallback<T> {
    T doCalculate(T result, String line) throws IOException;
}
