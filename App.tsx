import React from 'react';
import { SafeAreaView, Text } from 'react-native';
import CustomColorView from './Components/CustomColorView';

const App = () => (
  <SafeAreaView style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
    <Text style={{ marginBottom: 8 }}>↓ Native Kotlin View</Text>
    <CustomColorView style={{ width: 200, height: 200 }} color="#00FF7F" />
  </SafeAreaView>
);

export default App;
