package context.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface InputStreamSource {

    InputStream getInputStream() throws IOException;
}
