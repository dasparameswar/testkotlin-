import React, { useState } from 'react';
import { View, Button } from 'react-native';
import CustomColorView from './Components/CustomColorView';

export default function App() {
  const [color, setColor] = useState('#00FF00');

  return (
    <View style={{ flex: 1 }}>
      <CustomColorView color={color} style={{ flex: 1 }} />
      <Button title="Change to Red" onPress={() => setColor('#FF0000')} />
    </View>
  );
}
