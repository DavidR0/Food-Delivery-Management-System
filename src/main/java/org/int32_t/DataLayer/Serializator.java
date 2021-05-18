package org.int32_t.DataLayer;

import java.io.*;

/**
 * Generic class that serializes and deserializes an T Object
 * @param <T> Type of the class
 */
public class Serializator<T> {
    /**
     * Serializes the object
     * @param object object to be serialized
     * @param fileName name of the file
     */
        public void toSerial(T object, String fileName){
            FileOutputStream file = null;
            try {
                file = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(file);
                out.writeObject(object);
                out.close();
                file.close();
                System.out.println("Object serialized");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    /**
     * Deserializes an object from a file
     * @param object type of the object
     * @param fileName name of the file
     * @return the deserialized object
     */
        public T deserialize(T object, String fileName){

            try {
                FileInputStream file = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(file);
                object = (T) in.readObject();
                in.close();
                file.close();
                System.out.println("Object deserialized");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return object;
        }
}
