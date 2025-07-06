import { requireNativeComponent } from 'react-native';
import React from 'react';
import { ViewStyle } from 'react-native';

interface Props {
  color: string;
  style?: ViewStyle;
}

const CustomColorView = requireNativeComponent<Props>('CustomColorView');
export default CustomColorView;
