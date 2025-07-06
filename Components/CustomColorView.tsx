import { requireNativeComponent, ViewProps } from 'react-native';
import React from 'react';

interface Props extends ViewProps {
  color?: string;
}

const NativeComponent = requireNativeComponent<Props>('CustomColorView');

export default function CustomColorView(props: Props) {
  return <NativeComponent {...props} />;
}
