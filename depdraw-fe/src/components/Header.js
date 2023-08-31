import React from 'react';
import { Layout, Typography } from 'antd'; // Import Ant Design components
import './Header.css'; // You can define your header styles in this CSS file

const { Header: AntdHeader } = Layout;
const { Title } = Typography;

const Header = () => {
  return (
    <AntdHeader className="app-header">
      <div className="header-content">
        <Title level={2} style={{ color: 'white' }}>
          DepDraw
        </Title>
      </div>
    </AntdHeader>
  );
};

export default Header;
