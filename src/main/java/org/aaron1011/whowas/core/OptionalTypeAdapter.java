package org.aaron1011.whowas.core;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;

public class OptionalTypeAdapter<T> extends TypeAdapter<Optional<Date>> {

    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != Optional.class) {
                return null;
            }
            /*final ParameterizedType parameterizedType = (ParameterizedType) type.getType();
            final Type actualType = parameterizedType.getActualTypeArguments()[0];
            final TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(actualType));*/
            return new OptionalTypeAdapter();
        }
    };

    @Override
    public void write(JsonWriter out, Optional<Date> value) throws IOException {
        if(value.isPresent()){
            out.value(value.get().getTime());
        } else {
            out.nullValue();
        }
    }

    @Override
    public Optional<Date> read(JsonReader in) throws IOException {
        final JsonToken peek = in.peek();
        if(peek != JsonToken.NULL){
            return Optional.fromNullable(new Date(in.nextLong()));
        }
        return Optional.absent();
    }

}
