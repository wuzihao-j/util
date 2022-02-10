//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.fasterxml.jackson.dataformat.javaprop;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.base.ParserMinimalBase;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.dataformat.javaprop.io.JPropReadContext;
import com.fasterxml.jackson.dataformat.javaprop.util.JPropNode;
import com.fasterxml.jackson.dataformat.javaprop.util.JPropNodeBuilder;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class JavaPropsParser extends ParserMinimalBase {
    protected static final JavaPropsSchema DEFAULT_SCHEMA = new JavaPropsSchema();
    protected ObjectCodec _objectCodec;
    protected final Object _inputSource;
    protected final Properties _sourceProperties;
    protected JavaPropsSchema _schema;
    protected JPropReadContext _readContext;
    protected boolean _closed;
    protected ByteArrayBuilder _byteArrayBuilder;
    protected byte[] _binaryValue;

    public JavaPropsParser(IOContext ctxt, Object inputSource, int parserFeatures, ObjectCodec codec, Properties sourceProps) {
        super(parserFeatures);
        this._schema = DEFAULT_SCHEMA;
        this._objectCodec = codec;
        this._inputSource = inputSource;
        SortProperties sortProperties = new SortProperties();
        Set<Map.Entry<Object, Object>> entries = sourceProps.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            sortProperties.setProperty(entry.getKey().toString(), entry.getValue().toString());
        }
        this._sourceProperties = sortProperties;
    }

    public Version version() {
        return PackageVersion.VERSION;
    }

    public void setSchema(FormatSchema schema) {
        if (schema instanceof JavaPropsSchema) {
            this._schema = (JavaPropsSchema) schema;
        } else {
            super.setSchema(schema);
        }

    }

    public JavaPropsSchema getSchema() {
        return this._schema;
    }

    public void close() throws IOException {
        this._closed = true;
        this._readContext = null;
    }

    public boolean isClosed() {
        return this._closed;
    }

    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    public void setCodec(ObjectCodec c) {
        this._objectCodec = c;
    }

    public Object getInputSource() {
        return this._inputSource;
    }

    public boolean canUseSchema(FormatSchema schema) {
        return schema instanceof JavaPropsSchema;
    }

    public boolean requiresCustomCodec() {
        return false;
    }

    public boolean canReadObjectId() {
        return false;
    }

    public boolean canReadTypeId() {
        return false;
    }

    public JsonStreamContext getParsingContext() {
        return this._readContext;
    }

    public void overrideCurrentName(String name) {
        this._readContext.overrideCurrentName(name);
    }

    public String getCurrentName() throws IOException {
        if (this._readContext == null) {
            return null;
        } else {
            if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
                JPropReadContext parent = this._readContext.getParent();
                if (parent != null) {
                    return parent.getCurrentName();
                }
            }

            return this._readContext.getCurrentName();
        }
    }

    public JsonToken nextToken() throws IOException {
        if (this._readContext == null) {
            if (this._closed) {
                return null;
            }

            this._closed = true;
            JPropNode root = JPropNodeBuilder.build(this._schema, this._sourceProperties);
            this._readContext = JPropReadContext.create(root);
        }

        do {
            if ((this._currToken = this._readContext.nextToken()) != null) {
                return this._currToken;
            }

            this._readContext = this._readContext.nextContext();
        } while (this._readContext != null);

        return null;
    }

    public String getText() throws IOException {
        JsonToken t = this._currToken;
        if (t == JsonToken.VALUE_STRING) {
            return this._readContext.getCurrentText();
        } else if (t == JsonToken.FIELD_NAME) {
            return this._readContext.getCurrentName();
        } else {
            return t == null ? null : t.asString();
        }
    }

    public boolean hasTextCharacters() {
        return false;
    }

    public char[] getTextCharacters() throws IOException {
        String text = this.getText();
        return text == null ? null : text.toCharArray();
    }

    public int getTextLength() throws IOException {
        String text = this.getText();
        return text == null ? 0 : text.length();
    }

    public int getTextOffset() throws IOException {
        return 0;
    }

    public int getText(Writer writer) throws IOException {
        String str = this.getText();
        if (str == null) {
            return 0;
        } else {
            writer.write(str);
            return str.length();
        }
    }

    public byte[] getBinaryValue(Base64Variant variant) throws IOException {
        if (this._binaryValue == null) {
            if (this._currToken != JsonToken.VALUE_STRING) {
                this._reportError("Current token (" + this._currToken + ") not VALUE_STRING, can not access as binary");
            }

            ByteArrayBuilder builder = this._getByteArrayBuilder();
            this._decodeBase64(this.getText(), builder, variant);
            this._binaryValue = builder.toByteArray();
        }

        return this._binaryValue;
    }

    public ByteArrayBuilder _getByteArrayBuilder() {
        if (this._byteArrayBuilder == null) {
            this._byteArrayBuilder = new ByteArrayBuilder();
        } else {
            this._byteArrayBuilder.reset();
        }

        return this._byteArrayBuilder;
    }

    public Object getEmbeddedObject() throws IOException {
        return null;
    }

    public JsonLocation getTokenLocation() {
        return JsonLocation.NA;
    }

    public JsonLocation getCurrentLocation() {
        return JsonLocation.NA;
    }

    public Number getNumberValue() throws IOException {
        return (Number) this._noNumbers();
    }

    public NumberType getNumberType() throws IOException {
        return (NumberType) this._noNumbers();
    }

    public int getIntValue() throws IOException {
        return (Integer) this._noNumbers();
    }

    public long getLongValue() throws IOException {
        return (Long) this._noNumbers();
    }

    public BigInteger getBigIntegerValue() throws IOException {
        return (BigInteger) this._noNumbers();
    }

    public float getFloatValue() throws IOException {
        return (Float) this._noNumbers();
    }

    public double getDoubleValue() throws IOException {
        return (Double) this._noNumbers();
    }

    public BigDecimal getDecimalValue() throws IOException {
        return (BigDecimal) this._noNumbers();
    }

    protected <T> T _noNumbers() throws IOException {
        this._reportError("Current token (" + this._currToken + ") not numeric, can not use numeric value accessors");
        return null;
    }

    protected void _handleEOF() throws JsonParseException {
        if (this._readContext != null && !this._readContext.inRoot()) {
            this._reportInvalidEOF(": expected close marker for " + this._readContext.typeDesc(), (JsonToken) null);
        }

    }
}
