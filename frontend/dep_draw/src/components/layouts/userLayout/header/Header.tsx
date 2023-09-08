import React from 'react';
import {AntdHeaderStyle, TitleStyle} from './Header.Styles'

const Header = () => {
  return (
    <AntdHeaderStyle>
      <div className="header-content">
        <TitleStyle level={2}>
          DepDraw
        </TitleStyle>
      </div>
    </AntdHeaderStyle>
  );
};

export default Header;