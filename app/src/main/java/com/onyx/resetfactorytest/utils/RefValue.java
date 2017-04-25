package com.onyx.resetfactorytest.utils;

/**
 * Created by 12345 on 2017/4/24.
 */

public class RefValue<T>
{
    private T mValue = null;

    public RefValue()
    {
    }

    public RefValue(T v)
    {
        mValue = v;
    }

    public T getValue()
    {
        return mValue;
    }
    public void setValue(T v)
    {
        mValue = v;
    }
}
