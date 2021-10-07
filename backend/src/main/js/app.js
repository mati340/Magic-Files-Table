class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {employees: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/files'}).done(response => {
			this.setState({employees: response.entity._embedded.employees});
		});
	}

	render() {
		const files = this.props.files.map(file =>
			<File key={file._links.self.href} file={file}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Description</th>
					</tr>
					{files}
				</tbody>
			</table>
		)
	}
}