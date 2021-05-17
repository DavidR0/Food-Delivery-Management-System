package org.int32_t.DataLayer;

import java.io.*;

public class Serializator<T> {
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
