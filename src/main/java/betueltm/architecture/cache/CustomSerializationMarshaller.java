package betueltm.architecture.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.infinispan.commons.configuration.ClassWhiteList;
import org.infinispan.commons.dataconversion.MediaType;
import org.infinispan.commons.io.ByteBuffer;
import org.infinispan.commons.io.ByteBufferImpl;
import org.infinispan.commons.marshall.AbstractMarshaller;
import org.infinispan.commons.marshall.CheckedInputStream;

import betueltm.model.Car;

public class CustomSerializationMarshaller extends AbstractMarshaller {

	final ClassWhiteList whiteList;
	
	public CustomSerializationMarshaller() {
		this.whiteList = new ClassWhiteList();
		//TODO : make this generic
		whiteList.addClasses(Car.class, List.class, ArrayList.class);
	}

	@Override
	public Object objectFromByteBuffer(byte[] buf, int offset, int length) throws IOException, ClassNotFoundException {
		try (ObjectInputStream ois = new CheckedInputStream(new ByteArrayInputStream(buf), whiteList)) {
			return ois.readObject();
		}
	}

	@Override
	public boolean isMarshallable(Object o) throws Exception {
		return o instanceof Serializable;
	}

	@Override
	public MediaType mediaType() {
		return MediaType.APPLICATION_PROTOSTREAM;
	}

	@Override
	protected ByteBuffer objectToBuffer(Object o, int estimatedSize) throws IOException, InterruptedException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ObjectOutput out = new ObjectOutputStream(baos);
	    out.writeObject(o);
	    out.close();
	    baos.close();
	    return ByteBufferImpl.create(baos.toByteArray());
	}
}
