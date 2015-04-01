package agp32.dev.prop;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Property<T> extends PropertyBase<T> {

    public Property(T value) {
        this.value = value;
    }

    public T getValue() {
        return super.getValue();
    }

    public void setValue(T newValue) {
        super.setValue(newValue);
    }
}
