import java.lang.reflect.InvocationTargetException;

public interface IMethod {

    void applyMethod(java.lang.reflect.Method function, double left, double right, double accuracy) throws InvocationTargetException, IllegalAccessException;
}
