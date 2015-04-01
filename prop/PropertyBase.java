package agp32.dev.prop;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;

public abstract class PropertyBase<T> {

    @Getter(AccessLevel.PROTECTED) protected T value;

    protected void setValue(T newValue) {
        if(this.value != null &&
            this.value.equals(newValue)) return;

        T oldValue = this.value;
        this.value = newValue;
        notifyListeners(oldValue, newValue);
    }

    //
    //  Observable implementation
    //

    public interface ChangeListener<T> {
        public void call(PropertyBase<T> source, T oldValue, T newValue);
    }

    private final List<ChangeListener<T>> listeners = new ArrayList<ChangeListener<T>>();

    public boolean addListener(ChangeListener<T> listener) {
        return listeners.add(listener);
    }

    public boolean removeListener(ChangeListener<T> listener) {
        return listeners.remove(listener);
    }

    private void notifyListeners(T oldValue, T newValue) {
        for(ChangeListener<T> listener : listeners) {
            listener.call(this, oldValue, newValue);
        }
    }
}
