import React, { Component } from 'react';

class ToggleDescriptionWidget extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showDescription: false,
    };
  }

  toggleDescription = () => {
    this.setState((prevState) => ({
      showDescription: !prevState.showDescription,
    }));
  };

  render() {
    const { id, description } = this.props;
    const { showDescription } = this.state;

    return (
      <div>
        <div onClick={this.toggleDescription}>Toggle Description</div>
        {showDescription && <div id={id + '-description'}>{description}</div>}
      </div>
    );
  }
}

export default ToggleDescriptionWidget;
